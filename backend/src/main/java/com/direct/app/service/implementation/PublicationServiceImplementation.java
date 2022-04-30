package com.direct.app.service.implementation;

import com.direct.app.exceptions.RuntimeBusinessException;
import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.io.entities.UserDetailsEntity;
import com.direct.app.io.entities.UserEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.repositery.PublicationsRepository;
import com.direct.app.service.PublicationsService;
import com.direct.app.service.UserService;
import com.direct.app.service.util.publication_service_utils.PublishPostsUtil;
import com.direct.app.shared.dto.PublicationDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.direct.app.exceptions.ErrorCode.U$0009;
import static com.direct.app.exceptions.ErrorCode.U$0010;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@Service
public class PublicationServiceImplementation implements PublicationsService {

	@Autowired
	private PublicationsRepository publicationRepo;

	@Autowired
	private ConnectionRepository connectionRepo;

	@Autowired
	private PublishPostsUtil publishPostsUtil;

	@Autowired
	private UserService userService;

	@Override
	public List<PublicationDto> retrievePublications() throws Exception {
		Long userId = userService.getCurrentUserId();

		List<PublicationEntity> publicationEntities = publicationRepo.findByReceiverId(userId);

		List<PublicationDto> publications = generatePublicationDTOs(publicationEntities);

		return publications;
	}

	private List<PublicationDto> generatePublicationDTOs(List<PublicationEntity> publicationEntities) {
		List<PublicationDto> publications = new ArrayList<>();

		publicationEntities.forEach(entity -> {
			PublicationDto publicationDto = convertPublicationEntityToDTO(entity);
			publications.add(publicationDto);
		});

		return publications;
	}

	private PublicationDto convertPublicationEntityToDTO(PublicationEntity publicationEntity) {
		PublicationDto publicationDto = new PublicationDto();
		UserEntity sender = publicationEntity.getSender();
		UserDetailsEntity senderDetails = sender.getUserDetails();


		publicationDto.setId(publicationEntity.getId());
		publicationDto.setSenderId(sender.getId());
		publicationDto.setContent(publicationEntity.getContent());
		publicationDto.setLink(publicationEntity.getLink());
		publicationDto.setIsRead(publicationEntity.isRead());

		publicationDto.getSenderDetails().setFirstName(sender.getFirstName());
		publicationDto.getSenderDetails().setLastName(sender.getLastName());
		publicationDto.getSenderDetails().setProfessionalTitle(senderDetails.getProfessionalTitle());

		return publicationDto;
	}

	@Override
	public void publish(PublicationDto publication) throws Exception {
		PublicationDto publicationDto = new com.direct.app.shared.dto.PublicationDto();

		// Retrieve User Id
		long senderId = userService.getCurrentUserId();

		// Copy Properties from model to DTO
		BeanUtils.copyProperties(publication, publicationDto);
		publicationDto.setSenderId(senderId);

		// Delegate the whole function to the utility class "PublishPostsUtil"
		publishPostsUtil.publish(publicationDto);
	}
	
	@Transactional
	@Override
	public void markPublicationAsRead(Long publicationId) throws Exception {
		validatePublication(publicationId);
		publicationRepo.markPublicationAsRead(publicationId);

	}

	@Transactional
	@Override
	public void markPublicationAsUnRead(Long publicationId) throws Exception {
		validatePublication(publicationId);
        publicationRepo.markPublicationAsUnRead(publicationId);

	}

	private void validatePublication(Long publicationId) throws Exception {
		Long currentUserId = userService.getCurrentUserId();
		PublicationEntity publication =
				publicationRepo.findById(publicationId)
						.orElseThrow(() -> new RuntimeBusinessException(NOT_ACCEPTABLE, U$0009, publicationId));

		if(!currentUserId.equals(publication.getReceiver().getId()))
			throw new RuntimeBusinessException(NOT_ACCEPTABLE, U$0010, currentUserId, publicationId);

	}

}
