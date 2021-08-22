package es.codeurjc.topics.services;

import java.util.Collection;

import es.codeurjc.topics.dtos.requests.TopicRequestDto;
import es.codeurjc.topics.dtos.responses.TopicDetailsResponseDto;
import es.codeurjc.topics.dtos.responses.TopicResponseDto;

public interface TopicService {

    Collection<TopicResponseDto> findAll();

    TopicDetailsResponseDto save(TopicRequestDto topicRequestDto);

    TopicDetailsResponseDto findById(long topicId);

}
