package com.pfe.sequortalibfinal.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.pfe.sequortalibfinal.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.pfe.sequortalibfinal.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.pfe.sequortalibfinal.domain.User.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.Authority.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.User.class.getName() + ".authorities");
            createCache(cm, com.pfe.sequortalibfinal.domain.Module.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.Filiere.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.Filiere.class.getName() + ".modules");
            createCache(cm, com.pfe.sequortalibfinal.domain.Module.class.getName() + ".filieres");
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEtudiantModule.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEtudiantModule.class.getName() + ".modules");
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEnseignantModule.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEnseignantModule.class.getName() + ".modules");
            createCache(cm, com.pfe.sequortalibfinal.domain.Departement.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.Departement.class.getName() + ".filieres");
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEnseignantFiliere.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEnseignantFiliere.class.getName() + ".filieres");
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEtudiantFiliere.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEtudiantFiliere.class.getName() + ".filieres");
            createCache(cm, com.pfe.sequortalibfinal.domain.Etudiant.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEtudiantModule.class.getName() + ".etudiants");
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEtudiantFiliere.class.getName() + ".etudiants");
            createCache(cm, com.pfe.sequortalibfinal.domain.Enseignant.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEnseignantModule.class.getName() + ".enseignants");
            createCache(cm, com.pfe.sequortalibfinal.domain.HistoriqueEnseignantFiliere.class.getName() + ".enseignants");
            createCache(cm, com.pfe.sequortalibfinal.domain.Etablissement.class.getName());
            createCache(cm, com.pfe.sequortalibfinal.domain.Etablissement.class.getName() + ".etudiants");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
