package com.antonklintsevich.persistense;

import org.springframework.stereotype.Repository;

import com.antonklintsevich.entity.Genre;

@Repository
public class GenreRepository extends AbstractHibernateDao<Genre> {

}
