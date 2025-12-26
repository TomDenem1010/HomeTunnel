package com.home.tunnel.service;

import com.home.common.commonication.dto.CommunicationRequestContentDto;
import com.home.common.commonication.dto.CommunicationResponseContentDto;
import com.home.common.commonication.dto.CommunicationTransactionDto;
import com.home.common.commonication.mapper.CommunicationRequestContentMapper;
import com.home.common.commonication.mapper.CommunicationResponseContentMapper;
import com.home.common.commonication.mapper.CommunicationTransactionMapper;
import com.home.common.commonication.repository.CommunicationRequestContentRepository;
import com.home.common.commonication.repository.CommunicationResponseContentRepository;
import com.home.common.commonication.repository.CommunicationTransactionRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LoggingService {

  private final CommunicationTransactionRepository communicationTransactionRepository;
  private final CommunicationRequestContentRepository communicationRequestContentRepository;
  private final CommunicationResponseContentRepository communicationResponseContentRepository;
  private final CommunicationTransactionMapper communicationTransactionMapper;
  private final CommunicationRequestContentMapper communicationRequestContentMapper;
  private final CommunicationResponseContentMapper communicationResponseContentMapper;

  @Transactional
  public void logRequest(
      final CommunicationTransactionDto transactionDto,
      final CommunicationRequestContentDto requestContentDto) {
    communicationTransactionRepository.save(
        communicationTransactionMapper.toEntity(transactionDto));
    communicationRequestContentRepository.save(
        communicationRequestContentMapper.toEntity(requestContentDto));
  }

  @Transactional
  public void logResponse(
      final String transactionId, final CommunicationResponseContentDto responseContentDto) {
    communicationTransactionRepository.updateCommunicationTransactionResponseAt(
        LocalDateTime.now(), transactionId);
    communicationResponseContentRepository.save(
        communicationResponseContentMapper.toEntity(responseContentDto));
  }
}
