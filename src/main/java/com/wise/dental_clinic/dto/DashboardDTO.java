package com.wise.dental_clinic.dto;

public class DashboardDTO {
    private long totalPatients;
    private long totalDentists;
    private long totalAppointments;
    private long activeUsers;

    public DashboardDTO(long totalPatients, long totalDentists, long totalAppointments, long activeUsers) {
        this.totalPatients = totalPatients;
        this.totalDentists = totalDentists;
        this.totalAppointments = totalAppointments;
        this.activeUsers = activeUsers;
    }

    public long getTotalPatients() { return totalPatients; }
    public void setTotalPatients(long totalPatients) { this.totalPatients = totalPatients; }
    public long getTotalDentists() { return totalDentists; }
    public void setTotalDentists(long totalDentists) { this.totalDentists = totalDentists; }
    public long getTotalAppointments() { return totalAppointments; }
    public void setTotalAppointments(long totalAppointments) { this.totalAppointments = totalAppointments; }
    public long getActiveUsers() { return activeUsers; }
    public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }
}
