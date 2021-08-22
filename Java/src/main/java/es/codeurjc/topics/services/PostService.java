package es.codeurjc.topics.services;

import java.util.Collection;

import es.codeurjc.topics.dtos.requests.PostRequestDto;
import es.codeurjc.topics.dtos.responses.PostResponseDto;
import es.codeurjc.topics.dtos.responses.UserPostResponseDto;

public interface PostService {

    PostResponseDto addPost(long topicId, PostRequestDto postRequestDto);

    PostResponseDto deletePost(long topicId, long PostId);

    Collection<UserPostResponseDto> getPosts(Long userId);

}
