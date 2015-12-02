package com.example.appraise.service;

import com.example.appraise.api.RestApiException;
import com.example.appraise.dao.IndexDao;
import com.example.appraise.dao.UserDao;
import com.example.appraise.model.ArIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IndexService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private IndexDao indexDao;

    private void validate(ArIndex entity) throws RestApiException {
        String appraiser = entity.getAppraiser();
        String appraisee = entity.getAppraisee();
        if (userDao.findById(appraiser) == null)
            throw RestApiException.onInvalidParam("appraiser does not exist");
        if (!"".equals(appraisee) && userDao.findById(appraisee) == null)
            throw RestApiException.onInvalidParam("appraisee does not exist");
        if (entity.getPoint() <= 0)
            throw RestApiException.onInvalidParam("point must be greater than 0");
    }

    public void save(ArIndex entity) throws RestApiException {
        validate(entity);
        indexDao.save(entity);
    }

    public void update(ArIndex entity) throws RestApiException {
        if (indexDao.findById(entity.getId()) == null)
            throw RestApiException.onInvalidParam("index does not exist");
        if (indexDao.isUsed(entity.getId()))
            throw RestApiException.onInvalidParam("index is referenced, thus cannot be modified");
        validate(entity);
        indexDao.update(entity);
    }

    public List<ArIndex> findAll() {
        return indexDao.findAll();
    }

    public List<Integer> findUsedIndexes() {
        return indexDao.findUsedIndexes();
    }
}
