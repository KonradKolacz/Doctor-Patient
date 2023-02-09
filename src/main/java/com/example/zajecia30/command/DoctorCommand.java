package com.example.zajecia30.command;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCommand {
    private String name;
    private String surname;
    private String nip;
    private String specialty;
}
