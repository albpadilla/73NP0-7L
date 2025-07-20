package com.apadilla.tenpo.desafio.repository;

import com.apadilla.tenpo.desafio.entity.CallHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallHistoryRepository extends
        PagingAndSortingRepository<CallHistory, Long>,
        CrudRepository<CallHistory, Long> {
}
