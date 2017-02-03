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
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.orcid.jaxb.model.common_v2.Visibility;
import org.orcid.jaxb.model.record.summary_v2.ActivitiesSummary;
import org.orcid.jaxb.model.record_v2.Person;
import org.orcid.jaxb.model.record_v2.Record;
import org.orcid.test.DBUnitTest;
import org.orcid.test.OrcidJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

@RunWith(OrcidJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:orcid-core-context.xml" })
public class RecordManagerTest extends DBUnitTest {
    private static final List<String> DATA_FILES = Arrays.asList("/data/EmptyEntityData.xml", "/data/SecurityQuestionEntityData.xml",
            "/data/SourceClientDetailsEntityData.xml", "/data/ProfileEntityData.xml", "/data/WorksEntityData.xml", "/data/ClientDetailsEntityData.xml",
            "/data/Oauth2TokenDetailsData.xml", "/data/OrgsEntityData.xml", "/data/ProfileFundingEntityData.xml", "/data/OrgAffiliationEntityData.xml",
            "/data/PeerReviewEntityData.xml", "/data/GroupIdRecordEntityData.xml", "/data/RecordNameEntityData.xml", "/data/BiographyEntityData.xml");
    
    private static final String ORCID = "0000-0000-0000-0003";
    
    @Resource
    private RecordManager RecordManager;

    @BeforeClass
    public static void initDBUnitData() throws Exception {
        initDBUnitData(DATA_FILES);
    }

    @AfterClass
    public static void removeDBUnitData() throws Exception {
        Collections.reverse(DATA_FILES);
        removeDBUnitData(DATA_FILES);
    }
    
    @Test
    public void testGetRecord() {
        Record record = RecordManager.getRecord(ORCID);
        assertNotNull(record);
        assertNotNull(record.getActivitiesSummary());
        assertNotNull(record.getPerson());
        
        Person person = record.getPerson();
        assertNotNull(person.getExternalIdentifiers());
        assertNotNull(person.getExternalIdentifiers().getExternalIdentifiers());
        assertEquals(5, person.getExternalIdentifiers().getExternalIdentifiers().size());
        
        assertNotNull(person.getResearcherUrls());
        assertNotNull(person.getResearcherUrls().getResearcherUrls());
        assertEquals(5, person.getResearcherUrls().getResearcherUrls().size());
        
        assertNotNull(person.getOtherNames());
        assertNotNull(person.getOtherNames().getOtherNames());
        assertEquals(5, person.getOtherNames().getOtherNames().size());
        
        assertNotNull(person.getAddresses());
        assertNotNull(person.getAddresses().getAddress());
        assertEquals(5, person.getAddresses().getAddress().size());
        
        assertNotNull(person.getKeywords());
        assertNotNull(person.getKeywords().getKeywords());
        assertEquals(5, person.getKeywords().getKeywords().size());
        
        assertNotNull(person.getEmails());
        assertNotNull(person.getEmails().getEmails());
        assertEquals(5, person.getEmails().getEmails().size());
        
        assertNotNull(person.getBiography());
        assertEquals(Visibility.PUBLIC, person.getBiography().getVisibility());
        assertEquals("Biography for 0000-0000-0000-0003", person.getBiography().getContent());
        
        assertNotNull(person.getName());    
        assertNotNull(person.getName().getCreditName());
        assertEquals("Credit Name", person.getName().getCreditName().getContent());
        assertNotNull(person.getName().getFamilyName());
        assertEquals("Family Name", person.getName().getFamilyName().getContent());
        assertNotNull(person.getName().getGivenNames());
        assertEquals("Given Names", person.getName().getGivenNames().getContent());
        assertEquals(Visibility.PUBLIC, person.getName().getVisibility());  
        
        ActivitiesSummary activities = record.getActivitiesSummary();
        assertNotNull(activities);
        assertNotNull(activities.getEducations());
        assertNotNull(activities.getEducations().getSummaries());
        assertEquals(5, activities.getEducations().getSummaries().size());        
        
        assertNotNull(activities.getEmployments());
        assertNotNull(activities.getEmployments().getSummaries());
        assertEquals(5, activities.getEmployments().getSummaries().size());
        
        assertNotNull(activities.getFundings());
        assertNotNull(activities.getFundings().getFundingGroup());
        assertEquals(5, activities.getFundings().getFundingGroup().size());
        
        assertNotNull(activities.getPeerReviews());
        assertNotNull(activities.getPeerReviews().getPeerReviewGroup());
        assertEquals(5, activities.getPeerReviews().getPeerReviewGroup().size());
        
        assertNotNull(activities.getWorks());
        assertNotNull(activities.getWorks().getWorkGroup());
        assertEquals(5, activities.getWorks().getWorkGroup().size());
    }
    
    @Test
    public void testGetPublicRecord() {
        Record record = RecordManager.getPublicRecord(ORCID);
        assertNotNull(record);
        assertNotNull(record.getActivitiesSummary());
        assertNotNull(record.getPerson());
        
        Person person = record.getPerson();
        assertNotNull(person);
        
        assertNotNull(person.getExternalIdentifiers());
        assertNotNull(person.getExternalIdentifiers().getExternalIdentifiers());
        assertEquals(1, person.getExternalIdentifiers().getExternalIdentifiers().size());
        assertEquals(Long.valueOf(13), person.getExternalIdentifiers().getExternalIdentifiers().get(0).getPutCode());
        
        assertNotNull(person.getResearcherUrls());
        assertNotNull(person.getResearcherUrls().getResearcherUrls());
        assertEquals(1, person.getResearcherUrls().getResearcherUrls().size());
        assertEquals(Long.valueOf(13), person.getResearcherUrls().getResearcherUrls().get(0).getPutCode());
        
        assertNotNull(person.getOtherNames());
        assertNotNull(person.getOtherNames().getOtherNames());
        assertEquals(1, person.getOtherNames().getOtherNames().size());
        assertEquals(Long.valueOf(13), person.getOtherNames().getOtherNames().get(0).getPutCode());
        
        assertNotNull(person.getAddresses());
        assertNotNull(person.getAddresses().getAddress());
        assertEquals(1, person.getAddresses().getAddress().size());
        assertEquals(Long.valueOf(9), person.getAddresses().getAddress().get(0).getPutCode());
        
        assertNotNull(person.getKeywords());
        assertNotNull(person.getKeywords().getKeywords());
        assertEquals(1, person.getKeywords().getKeywords().size());
        assertEquals(Long.valueOf(9), person.getKeywords().getKeywords().get(0).getPutCode());
        
        assertNotNull(person.getEmails());
        assertNotNull(person.getEmails().getEmails());
        assertEquals(1, person.getEmails().getEmails().size());
        assertEquals("public_0000-0000-0000-0003@test.orcid.org", person.getEmails().getEmails().get(0).getEmail());
        
        assertNotNull(person.getBiography());
        assertEquals(Visibility.PUBLIC, person.getBiography().getVisibility());
        assertEquals("Biography for 0000-0000-0000-0003", person.getBiography().getContent());
        
        assertNotNull(person.getName());    
        assertNotNull(person.getName().getCreditName());
        assertEquals("Credit Name", person.getName().getCreditName().getContent());
        assertNotNull(person.getName().getFamilyName());
        assertEquals("Family Name", person.getName().getFamilyName().getContent());
        assertNotNull(person.getName().getGivenNames());
        assertEquals("Given Names", person.getName().getGivenNames().getContent());
        assertEquals(Visibility.PUBLIC, person.getName().getVisibility());
        
        ActivitiesSummary activities = record.getActivitiesSummary();
        assertNotNull(activities.getEducations());
        assertNotNull(activities.getEducations().getSummaries());
        assertEquals(1, activities.getEducations().getSummaries().size());
        assertEquals(Long.valueOf(20), activities.getEducations().getSummaries().get(0).getPutCode());
        
        assertNotNull(activities.getEmployments());
        assertNotNull(activities.getEmployments().getSummaries());
        assertEquals(1, activities.getEmployments().getSummaries().size());
        assertEquals(Long.valueOf(17), activities.getEmployments().getSummaries().get(0).getPutCode());
        
        assertNotNull(activities.getFundings());
        assertNotNull(activities.getFundings().getFundingGroup());
        assertEquals(1, activities.getFundings().getFundingGroup().size());
        assertEquals(Long.valueOf(10), activities.getFundings().getFundingGroup().get(0).getFundingSummary().get(0).getPutCode());
        
        assertNotNull(activities.getPeerReviews());
        assertNotNull(activities.getPeerReviews().getPeerReviewGroup());
        assertEquals(1, activities.getPeerReviews().getPeerReviewGroup().size());
        assertEquals(Long.valueOf(9), activities.getPeerReviews().getPeerReviewGroup().get(0).getPeerReviewSummary().get(0).getPutCode());
        
        assertNotNull(activities.getWorks());
        assertNotNull(activities.getWorks().getWorkGroup());
        assertEquals(1, activities.getWorks().getWorkGroup().size());
        assertEquals(Long.valueOf(11), activities.getWorks().getWorkGroup().get(0).getWorkSummary().get(0).getPutCode());
    }
}
