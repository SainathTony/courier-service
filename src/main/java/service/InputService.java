package service;

import model.CourierInput;

public interface InputService<T> {
    CourierInput readInputFromUser();
}
