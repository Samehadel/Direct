package com.direct.app.service.implementation;

import java.util.ArrayList;
import java.util.List;

import com.direct.app.service.IUserService;
import com.direct.app.service.util.publication_service_utils.PublishPostsUtil;
import com.direct.app.repositery.PublicationsRepository;
import com.direct.app.ui.models.request.PublicationRequestModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.direct.app.io.entities.PublicationEntity;
import com.direct.app.repositery.ConnectionRepository;
import com.direct.app.service.IPublicationsService;
import com.direct.app.shared.dto.PublicationDto;
import com.direct.app.ui.models.response.PublicationResponseModel;

@Service
public class PublicationServiceImplementation implements IPublicationsService {

	@Autowired 
	private PublicationsRepository publicationRepo;
	
	@Autowired
	private ConnectionRepository connectionRepo;

	@Autowired
	private PublishPostsUtil publishPostsUtil;

	@Autowired
	private IUserService userService;

	@Override
	public List<PublicationResponseModel> retrievePublications() throws Exception {

		List<PublicationResponseModel> publications = new ArrayList<>();

		// Get user's id from security context holder
		long userId = userService.retrieveUserId();

		//Repository use
		List<PublicationEntity> publicationEntities = publicationRepo.findByRecieverId(userId);
		
		for(PublicationEntity entity: publicationEntities) {
			PublicationResponseModel model = new PublicationResponseModel();
			
			//Copy relevant attributes
			BeanUtils.copyProperties(entity, model);
			model.setSenderId(entity.getSender().getId());

			//Append publications
			publications.add(model);
		}
		
		return publications;
	}

	@Override
	public void publish(PublicationRequestModel publication) throws Exception {
		PublicationDto publicationDto = new PublicationDto();

		// Retrieve User Id
		long senderId = userService.retrieveUserId();

		// Copy Properties from model to DTO
		BeanUtils.copyProperties(publication, publicationDto);
		publicationDto.setSenderId(senderId);

		// Delegate the whole function to the utility class "PublishPostsUtil"
		publishPostsUtil.publish(publicationDto);
	}

}
