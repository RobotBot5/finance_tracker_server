package com.robotbot.finance_tracker_server.mappers;

public interface Mapper<A, B> {

    B mapDtoToEntity(A a);

    A mapEntityToDto(B b);

}
