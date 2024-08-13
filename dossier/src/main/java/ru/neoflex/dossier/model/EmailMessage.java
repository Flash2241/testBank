package ru.neoflex.dossier.model;

import lombok.Data;
import org.springframework.ui.context.Theme;

@Data
public class EmailMessage {
    private String address;
    private Theme theme;
    private Long statasmentId;

}
