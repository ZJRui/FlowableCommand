package com.flowable.common.cfg;

import com.flowable.common.Command;
import com.flowable.common.session.Session;
import com.flowable.common.session.SessionFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandContext {


    protected Command<?> command;
    protected Map<Class<?>, SessionFactory> sessionFactories;
    protected Map<Class<?>, Session> sessions = new HashMap<>();


    public CommandContext(Command<?> cmd) {
        this.command=cmd;
    }


    public Command<?> getCommand() {
        return command;
    }

    public void setCommand(Command<?> command) {
        this.command = command;
    }

    public Map<Class<?>, SessionFactory> getSessionFactories() {
        return sessionFactories;
    }

    public void setSessionFactories(Map<Class<?>, SessionFactory> sessionFactories) {
        this.sessionFactories = sessionFactories;
    }

    public Map<Class<?>, Session> getSessions() {
        return sessions;
    }

    public void setSessions(Map<Class<?>, Session> sessions) {
        this.sessions = sessions;
    }
}
