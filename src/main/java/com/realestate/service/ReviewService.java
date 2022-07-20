package com.realestate.service;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.realestate.exception.ResourceNotFoundException;
import com.realestate.exception.message.ErrorMessage;
import com.realestate.domain.Property;
import com.realestate.domain.Review;
import com.realestate.domain.User;
import com.realestate.domain.enums.ReviewStatus;
import com.realestate.dto.request.ReviewRequest;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.ReviewRepository;
import com.realestate.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewService {

	private ReviewRepository reviewRepository;
	private UserRepository userRepository;
	private PropertyRepository propertyRepository;
	
	public void createReview(Long userId, @Valid ReviewRequest reviewRequest, Long propertyId) {
		User user = userRepository.findById(userId).orElseThrow(()->new 
				ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, userId)));
		
		 Property property= propertyRepository.findById(propertyId).orElseThrow(()->
         new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, propertyId)));
		
		
		Review review = new Review();
		review.setPropertyId(property);
		review.setReview(reviewRequest.getReview());
		review.setScore(reviewRequest.getScore());
		review.setCreateDate(LocalDateTime.now());
		review.setStatus(ReviewStatus.PENDING);
		review.setUserId(user);
		
		reviewRepository.save(review);
		
		
	}

}