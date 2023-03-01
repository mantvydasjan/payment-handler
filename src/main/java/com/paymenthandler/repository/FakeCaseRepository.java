package com.paymenthandler.repository;

import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import com.paymenthandler.model.Resolution;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;


public class FakeCaseRepository implements CaseRepository {

    List<Case> cases = new ArrayList();

    @Override
    public <S extends Case> S save(S aCase) {
        cases.add(aCase);
        return aCase;
    }

    @Override
    public Optional<Case> findById(UUID id) {
        return cases.stream().filter(aCase -> aCase.getCaseId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public <S extends Case> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public List<Case> findAll() {
        return cases;
    }

    @Override
    public List<Case> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID id) {
        Case aCase1 = cases.stream().filter(aCase -> aCase.getCaseId().equals(id)).findFirst().get();
        cases.remove(aCase1);
    }

    @Override
    public void delete(Case entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Case> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Case> findByCountry(Country country) {
        return cases.stream().filter(aCase -> aCase.getCountry().equals(country)).toList();
    }

    @Override
    public List<Case> findByResolution(Resolution resolution) {
        return cases.stream().filter(aCase -> aCase.getResolution().equals(resolution)).toList();
    }

    @Override
    public void flush() {
        
    }

    @Override
    public <S extends Case> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Case> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Case> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Case getOne(UUID uuid) {
        return null;
    }

    @Override
    public Case getById(UUID uuid) {
        return null;
    }

    @Override
    public Case getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Case> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Case> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Case> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Case> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Case> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Case> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Case, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Case> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Case> findAll(Pageable pageable) {
        return null;
    }
}

   