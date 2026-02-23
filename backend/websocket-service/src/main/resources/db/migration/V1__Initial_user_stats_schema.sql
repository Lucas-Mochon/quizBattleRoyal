-- WebSocket Service: User Stats, Power-ups, and Achievements
CREATE TABLE IF NOT EXISTS user_stats (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    total_score INT NOT NULL DEFAULT 0,
    total_wins INT NOT NULL DEFAULT 0,
    total_games INT NOT NULL DEFAULT 0,
    ranking INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_power_ups (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    power_up_id UUID NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    last_used_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, power_up_id)
);

CREATE TABLE IF NOT EXISTS user_achievements (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    achievement_id UUID NOT NULL,
    UNIQUE(user_id, achievement_id)
);

CREATE INDEX idx_user_stats_user_id ON user_stats(user_id);
CREATE INDEX idx_user_power_ups_user_id ON user_power_ups(user_id);
CREATE INDEX idx_user_achievements_user_id ON user_achievements(user_id);
