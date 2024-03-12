package com.backend.DonoDash.model;

import com.backend.DonoDash.enums.SettingValue;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_live_feed_refresh_rate")
@Entity
public class FeedRefreshRate extends BaseEntity{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private SettingValue value;
}
