package com.assignment.resourcemanagement.broker;

public interface MessageBroker {
  void send(String topic, Object payload);
}
