package com.home.tunnel.controller;

import com.home.common.dto.HomeResponse;
import com.home.common.video.dto.findActors.FindActorsRequest;
import com.home.common.video.dto.findActors.FindActorsResponse;
import com.home.common.video.dto.findByActor.FindByActorRequestWrapper;
import com.home.common.video.dto.findByActor.FindByActorResponse;
import com.home.common.video.dto.findByFolder.FindByFolderRequestWrapper;
import com.home.common.video.dto.findByFolder.FindByFolderResponse;
import com.home.common.video.dto.findFolders.FindFoldersRequest;
import com.home.common.video.dto.findFolders.FindFoldersResponse;
import com.home.common.video.dto.findVideos.FindVideosRequest;
import com.home.common.video.dto.findVideos.FindVideosResponse;
import com.home.tunnel.client.VideoClient;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/video")
@AllArgsConstructor
@Slf4j(topic = "VIDEO")
public class VideoController {

	private final VideoClient videoClient;

	@PostMapping("/findVideos")
	public HomeResponse<FindVideosResponse> findVideos(final @RequestBody FindVideosRequest homeRequest) {
		log.debug("VideoController::findVideos {}", homeRequest);
		return videoClient.findVideos(homeRequest);
	}

	@PostMapping("/findFolders")
	public HomeResponse<FindFoldersResponse> findFolders(
			final @RequestBody FindFoldersRequest homeRequest) {
		log.debug("VideoController::findFolders {}", homeRequest);
		return videoClient.findFolders(homeRequest);
	}

	@PostMapping("/findActors")
	public HomeResponse<FindActorsResponse> findActors(final @RequestBody FindActorsRequest homeRequest) {
		log.debug("VideoController::findActors {}", homeRequest);
		return videoClient.findActors(homeRequest);
	}

	@PostMapping("/findByFolder")
	public HomeResponse<FindByFolderResponse> findVideosByFolder(
			final @RequestBody FindByFolderRequestWrapper homeRequest) {
		log.debug("VideoController::findVideosByFolder {}", homeRequest);
		return videoClient.findByFolder(homeRequest);
	}

	@PostMapping("/findByActor")
	public HomeResponse<FindByActorResponse> findVideosByActor(
			final @RequestBody FindByActorRequestWrapper homeRequest) {
		log.debug("VideoController::findVideosByActor {}", homeRequest);
		return videoClient.findByActor(homeRequest);
	}

	@GetMapping(value = "/stream/**")
	public ResponseEntity<Resource> streamVideo(
			HttpServletRequest request,
			@RequestHeader(value = "Range", required = true) String rangeHeader) {
		log.debug("VideoController::streamVideo {} {}", request.getRequestURI(), rangeHeader);
		return videoClient.streamVideo(
				UriUtils.decode(
						request.getRequestURI()
								.substring(request.getRequestURI().indexOf("/stream/") + "/stream/".length()),
						StandardCharsets.UTF_8),
				rangeHeader);
	}
}
