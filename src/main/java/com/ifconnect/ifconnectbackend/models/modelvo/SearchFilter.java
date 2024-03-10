package com.ifconnect.ifconnectbackend.models.modelvo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.control.Option;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import static io.vavr.control.Option.of;

public class SearchFilter {

    @JsonProperty("order")
    private String order;

    @Getter
    @JsonProperty("size")
    private Integer size;

    @Getter
    @JsonProperty("page")
    private Integer page;

    @Getter
    @JsonProperty("filter")
    private final Option<String> filter;

    @JsonCreator
    public SearchFilter(@JsonProperty(value = "filter") String filter,
                        @JsonProperty(value = "order") String order,
                        @JsonProperty(value = "page") Integer page,
                        @JsonProperty(value = "size") Integer size) {
        this.filter = of("%" + filter + "%");
        this.order = order;
        this.page = page;
        this.size = size;
    }

    public String getOrder() {
        return order.startsWith("-") ? order.substring(1) : order;
    }

    public Sort.Direction getDirection() {
        return this.order.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

}