package com.openclassroom.mareu;


import com.openclassroom.mareu.di.DI;
import com.openclassroom.mareu.model.Meeting;
import com.openclassroom.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class MeetingListUnitTest {

    private MeetingApiService mMeetingApiService;

    @Before
    public void setup() {
        mMeetingApiService = DI.getNewMeetingApiService();
    }

    @Test
    public void getDefaultList() {
        assertEquals(12, mMeetingApiService.getMeetings().size());
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = mMeetingApiService.getMeetings().get(0);
        mMeetingApiService.removeMeeting(meetingToDelete);
        assertFalse(mMeetingApiService.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        int size = mMeetingApiService.getMeetings().size();
        Meeting meetingToAdd = new Meeting("19/07/2020", "08h40", "4", "Test",
                Arrays.asList("jean@email.com", "lea@email.com", "nina@email.com"));
        mMeetingApiService.createMeeting(meetingToAdd);
        assertEquals(mMeetingApiService.getMeetings().size(), size + 1);
    }
}
