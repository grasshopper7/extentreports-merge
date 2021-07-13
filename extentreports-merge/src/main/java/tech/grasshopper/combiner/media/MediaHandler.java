package tech.grasshopper.combiner.media;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import com.aventstack.extentreports.model.Media;

import lombok.Builder;
import lombok.Data;
import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.options.CombinerOptions;
import tech.grasshopper.combiner.pojo.Test;

@Data
@Builder
public class MediaHandler {

	private Test selectedScenarioTest;

	private Path selectedScenarioTestMediaFolder;

	private Test replacedScenarioTest;

	private Path replacedScenarioTestMediaFolder;

	private String sourceMediaFileExtension;

	private CombinerOptions options;

	public static final String MEDIA_FOLDER = "media";

	private static int MEDIA_NAME_COUNTER = 0;

	private static final String MEDIA_NAME_PREFIX = "embedded";

	public void copyReportMedias() {
		List<Media> medias = selectedScenarioTest.getChildren().stream()
				.flatMap(c -> c.getLogs().stream().map(l -> l.getMedia()).filter(m -> m != null))
				.collect(Collectors.toList());

		medias.forEach(m -> {
			try {
				Files.copy(sourceMediaFilePathName(m), targetMediaFilePathName(), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				throw new CombinerException("Unable to copy media files.", e);
			}
			m.setResolvedPath("");
			m.setPath(MEDIA_FOLDER + "/" + MEDIA_NAME_PREFIX + MEDIA_NAME_COUNTER + sourceMediaFileExtension);
		});

		removeReplacedScenarioTestMedias();
	}

	private void removeReplacedScenarioTestMedias() {
		replacedScenarioTest.getChildren().stream()
				.flatMap(c -> c.getLogs().stream().map(l -> l.getMedia()).filter(m -> m != null)).forEach(m -> {

					if (Paths.get(options.getMergedReportFolderPath().toString(), MEDIA_FOLDER)
							.equals(replacedScenarioTestMediaFolder)) {
						try {
							Files.deleteIfExists(
									Paths.get(options.getMergedReportFolderPath().toString(), m.getPath()));
						} catch (Exception e) {
							// Silent intentionally. Not a big deal if file is undeleted. Maybe log this.
							e.printStackTrace();
						}
					}

				});
	}

	private Path sourceMediaFilePathName(Media media) {
		String mediaPath = "";

		if (media.getResolvedPath() != null && !media.getResolvedPath().isEmpty())
			mediaPath = media.getResolvedPath();
		else if (media.getPath() != null && !media.getPath().isEmpty())
			mediaPath = media.getPath();
		else
			throw new CombinerException("Unable to retrieve media path.");

		sourceMediaFileExtension(mediaPath);
		return sourceMediaFilePathName(mediaPath);
	}

	private void sourceMediaFileExtension(String mediaPath) {
		sourceMediaFileExtension = mediaPath.substring(mediaPath.lastIndexOf("."));
	}

	private Path sourceMediaFilePathName(String mediaPath) {
		return Paths.get(selectedScenarioTestMediaFolder.toString(), Paths.get(mediaPath).getFileName().toString());
	}

	private Path targetMediaFilePathName() {
		MEDIA_NAME_COUNTER++;

		return Paths.get(options.getMergedReportFolderPath().toString(), MEDIA_FOLDER,
				MEDIA_NAME_PREFIX + MEDIA_NAME_COUNTER + sourceMediaFileExtension);
	}
}
