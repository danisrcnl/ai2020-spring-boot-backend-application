package it.polito.ai.lab2.services;

import it.polito.ai.lab2.dataStructures.MemberStatus;
import it.polito.ai.lab2.dtos.TeamDTO;

import java.util.List;

public interface NotificationService {

    void sendMessage(String address, String subject, String body);
    boolean confirm(String token);
    boolean reject(String token);
    void notifyTeam(String courseName, String teamName, List<String> memberIds, int hours);
    List<MemberStatus> getMembersStatus(int teamId);
}
