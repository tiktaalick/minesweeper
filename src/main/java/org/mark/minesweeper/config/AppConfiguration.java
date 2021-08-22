package org.mark.minesweeper.config;

import org.mark.minesweeper.logic.MineLogic;
import org.mark.minesweeper.service.MineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public MineLogic mineLogicBean() {
        return new MineLogic();
    }

    @Bean
    public MineService mineServiceBean() {
        return new MineService();
    }
}
