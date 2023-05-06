package com.bugtracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugtracking.entity.Application;
import com.bugtracking.entity.Release;
import com.bugtracking.exception.ApplicationNotFoundException;
import com.bugtracking.exception.ReleaseNotFoundException;
import com.bugtracking.model.ApplicationVO;
import com.bugtracking.model.ReleaseRequest;
import com.bugtracking.model.ReleaseVO;
import com.bugtracking.repository.ReleaseRepository;

@Service
public class ReleaseServiceImpl implements IReleaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseServiceImpl.class);

	@Autowired
	ReleaseRepository releaseRepository;

	@Override
	public List<ReleaseVO> findAll() {

		LOGGER.info("Inside ReleaseServiceImpl findAll method ...");
		List<Release> releases = releaseRepository.findAll();
		LOGGER.info("Fetching all releases response : {}", releases);

		List<ReleaseVO> releaseVOS = releases.parallelStream().map(release -> {

			ReleaseVO releaseVO = new ReleaseVO();

			releaseVO.setId(release.getId());
			releaseVO.setName(release.getName());
			releaseVO.setReleaseDate(release.getReleaseDate());
			releaseVO.setStatus(release.getStatus());
			// releaseVO.setBugs(release.getBugs());
			releaseVO.setComments(release.getComments());

			return releaseVO;
		}).collect(Collectors.toList());
		return releaseVOS;
	}

	@Override
	public ReleaseVO findById(int releaseId) throws ReleaseNotFoundException {
		LOGGER.info("Inside ReleaseServiceImpl findById method ...");
		Optional<Release> release = releaseRepository.findById(releaseId);
		LOGGER.info("Fetching a release response : {}", release);
		if (!release.isPresent()) {
			LOGGER.error("No such release present !!!");
			throw new ReleaseNotFoundException("No such release present !!!");
		} else {
			ReleaseVO releaseVO = new ReleaseVO();
			releaseVO.setId(release.get().getId());
			releaseVO.setName(release.get().getName());
			releaseVO.setReleaseDate(release.get().getReleaseDate());
			releaseVO.setStatus(release.get().getStatus());
			// releaseVO.setBugs(release.get().getBugs());
			releaseVO.setComments(release.get().getComments());

			return releaseVO;
		}
	}

	@Override
	public ReleaseVO save(ReleaseRequest releaseRequest) throws ReleaseNotFoundException {
		LOGGER.info("Inside the ReleaseServiceImpl.save method and params, releaseRequest:{}", releaseRequest);

		if (releaseRequest == null) {
			LOGGER.info("Invalid release request");
			throw new ReleaseNotFoundException("Invalid release request");
		}

		Release release = new Release();
		if (releaseRequest.getId() > 0) {
			release.setId(releaseRequest.getId());
		}
		release.setName(releaseRequest.getName());
		release.setReleaseDate(releaseRequest.getReleaseDate());
		release.setStatus(releaseRequest.getStatus());
		// release.setBugs(releaseRequest.getBugs());
		release.setComments(releaseRequest.getComments());

		Release releaseResponse = releaseRepository.save(release);
		ReleaseVO releaseVO = null;
		if (releaseResponse != null) {
			LOGGER.info("Release Response, releaseResponse:{}", releaseResponse);
			releaseVO = new ReleaseVO();

			releaseVO.setId(release.getId());
			releaseVO.setName(release.getName());
			releaseVO.setReleaseDate(release.getReleaseDate());
			releaseVO.setStatus(release.getStatus());
			// releaseVO.setBugs(release.getBugs());
			releaseVO.setComments(release.getComments());

		}
		return releaseVO;
	}

	@Override
	public String delete(int id) throws ReleaseNotFoundException {
		LOGGER.info("Input to ReleaseServiceImpl.delete, id:{}", id);
		if (id < 0) {
			LOGGER.info("Invalid release id");
			throw new ReleaseNotFoundException("Invalid release id");
		}
		try {
			releaseRepository.deleteById(id);
		} catch (Exception ex) {
			LOGGER.error("Exception while deleting release");
			throw new ReleaseNotFoundException("Exception while deleting release");
		}
		return "Release has been deleted";
	}

	@Override
	public ReleaseVO findByName(String name) throws ReleaseNotFoundException {
		LOGGER.info("Inside ReleaseServiceImpl.findByName and name: {}", name);
		ReleaseVO releaseVO = null;
		if (name == null) {
			LOGGER.info("Invalid release name:{}", name);
			throw new ReleaseNotFoundException("Release name is not valid");
		}
		Optional<Release> release = releaseRepository.findByName(name);
		LOGGER.info("Default findByName invoked Successfully !!!");
		if (release.isPresent()) {
			LOGGER.info("Release details for the name {} and the values :{}", name, release.get());
			releaseVO = new ReleaseVO();

			releaseVO.setId(release.get().getId());
			releaseVO.setName(release.get().getName());
			releaseVO.setReleaseDate(release.get().getReleaseDate());
			releaseVO.setStatus(release.get().getStatus());
			// releaseVO.setBugs(release.get().getBugs());
			releaseVO.setComments(release.get().getComments());
		}
		return releaseVO;
	}

	@Override
	public ReleaseVO findMyReleaseByName(String name) throws ReleaseNotFoundException {
		LOGGER.info("Inside ReleaseServiceImpl.findMyReleaseByName and name: {}", name);
		ReleaseVO releaseVO = null;
		if (name == null) {
			LOGGER.info("Invalid release name:{}", name);
			throw new ReleaseNotFoundException("Release name is not valid");
		}
		Optional<Release> release = releaseRepository.findByName(name);
		LOGGER.info("Custom findMyReleaseByName invoked Successfully !!!");
		if (release.isPresent()) {
			LOGGER.info("Release details for the name {} and the values :{}", name, release.get());
			releaseVO = new ReleaseVO();

			releaseVO.setId(release.get().getId());
			releaseVO.setName(release.get().getName());
			releaseVO.setReleaseDate(release.get().getReleaseDate());
			releaseVO.setStatus(release.get().getStatus());
			// releaseVO.setBugs(release.get().getBugs());
			releaseVO.setComments(release.get().getComments());
		}
		return releaseVO;
	}
}