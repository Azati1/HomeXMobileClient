package com.bsaldevs.mobileclient.Net;

public class Request {
    private String sender;
    private Command command;
    private String address;

    public Request(String sender, String address, Command command) {
        this.command = command;
        this.address = address;
        this.sender = sender;
    }

    @Override
    public String toString() {
        return sender + ":" + address + ":" + command;
    }

    public Command getCommand() {
        return command;
    }

    public String getSender() {
        return sender;
    }

    public String getAddress() {
        return address;
    }

    public static Request parse(String value) {

        String sender = "";
        String address = "";
        String command = "";

        int i = 0;

        while (value.charAt(i) != ':')
            sender += value.charAt(i++);
        i++;
        while (value.charAt(i) != ':')
            address += value.charAt(i++);
        i++;
        while (value.length() > i) {
            command += value.charAt(i++);
        }

        Command command1 = Command.parse(command);
        Request request = new Request(sender, address, command1);

        return request;
    }
}