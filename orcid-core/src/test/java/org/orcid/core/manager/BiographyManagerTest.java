/**
 * =============================================================================
 *
 * ORCID (R) Open Source
 * http://orcid.org
 *
 * Copyright (c) 2012-2014 ORCID, Inc.
 * Licensed under an MIT-Style License (MIT)
 * http://orcid.org/open-source-license
 *
 * This copyright and license information (including a link to the full license)
 * shall be included in its entirety in all copies or substantial portion of
 * the software.
 *
 * =============================================================================
 */
package org.orcid.core.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.orcid.core.BaseTest;
import org.orcid.jaxb.model.common_v2.Visibility;
import org.orcid.jaxb.model.record_v2.Biography;

public class BiographyManagerTest extends BaseTest {
    @Resource
    private BiographyManager biographyManager;
    
    @BeforeClass
    public static void initDBUnitData() throws Exception {
        initDBUnitData(Arrays.asList("/data/SecurityQuestionEntityData.xml", "/data/SourceClientDetailsEntityData.xml",
                "/data/ProfileEntityData.xml", "/data/BiographyEntityData.xml"));
    }

    @AfterClass
    public static void removeDBUnitData() throws Exception {
        removeDBUnitData(Arrays.asList("/data/BiographyEntityData.xml", "/data/ProfileEntityData.xml", "/data/SourceClientDetailsEntityData.xml", 
                "/data/SecurityQuestionEntityData.xml"));
    }
    
    @Test
    public void testCreateBiography() {
        String orcid = "0000-0000-0000-0004";
        Biography bio = new Biography();        
        bio.setContent("This is my biography");
        bio.setVisibility(Visibility.LIMITED);
        
        biographyManager.createBiography(orcid, bio);
        Biography newBio = biographyManager.getBiography(orcid, 0);
        assertNotNull(newBio);
        assertEquals("This is my biography", newBio.getContent());
        assertEquals(Visibility.LIMITED, newBio.getVisibility());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFailOnCreatingOnARecordThatAlreadyHavebiography() {
        String orcid = "0000-0000-0000-0003";
        Biography bio = new Biography();        
        bio.setContent("This is my biography");
        bio.setVisibility(Visibility.LIMITED);
        
        biographyManager.createBiography(orcid, bio);
        fail();
    }
    
    @Test
    public void testExists() {
        assertTrue(biographyManager.exists("0000-0000-0000-0001"));
        assertTrue(biographyManager.exists("0000-0000-0000-0002"));
        assertTrue(biographyManager.exists("0000-0000-0000-0003"));        
        
        assertFalse(biographyManager.exists("0000-0000-0000-1000"));
        assertFalse(biographyManager.exists("0000-0000-0000-1001"));
        assertFalse(biographyManager.exists("0000-0000-0000-1002"));
        assertFalse(biographyManager.exists("0000-0000-0000-1003"));
        assertFalse(biographyManager.exists("0000-0000-0000-1004"));
    }
    
    @Test
    public void testGetBiography() {
        String orcid = "0000-0000-0000-0002";
        Biography bio = biographyManager.getBiography(orcid, 0);
        assertNotNull(bio);
        assertEquals("Biography for 0000-0000-0000-0002", bio.getContent());
        assertEquals(Visibility.LIMITED, bio.getVisibility());
    }
    
    @Test
    public void testGetPublicBiography() {
        String orcid = "0000-0000-0000-0002";
        Biography bio = biographyManager.getPublicBiography(orcid, 0);
        assertNull(bio);
        
        orcid = "0000-0000-0000-0003";
        bio = biographyManager.getPublicBiography(orcid, 0);
        assertNotNull(bio);
        assertEquals("Biography for 0000-0000-0000-0003", bio.getContent());
        assertEquals(Visibility.PUBLIC, bio.getVisibility());
    }
    
    @Test
    public void testUpdateBiography() {
        String orcid = "0000-0000-0000-0001";
        Biography bio = biographyManager.getBiography(orcid, 0);
        assertNotNull(bio);
        assertEquals("Biography for 0000-0000-0000-0001", bio.getContent());
        assertEquals(Visibility.PRIVATE, bio.getVisibility());
        
        long now = System.currentTimeMillis();
        bio.setContent("Updated bio " + now);
        bio.setVisibility(Visibility.PUBLIC);
        biographyManager.updateBiography(orcid, bio);
        
        Biography newBio = biographyManager.getBiography(orcid, 0);
        assertNotNull(newBio);
        assertEquals("Updated bio " + now, newBio.getContent());
        assertEquals(Visibility.PUBLIC, newBio.getVisibility());
    }        
}
