package br.edu.tasima.rotatracker.model;

import java.time.LocalDateTime;

import br.edu.tasima.rotatracker.LogHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class LocationInfo {
    private String provider;
    private double latitude;
    private double longitude;
    private float accuracy;
    private LocalDateTime time;

    @Override
    public String toString() {
        return LogHelper.FormatLocationInfo(this);
    }

}
