package com.porto.ecor.mensageria.dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
public class CorrelationId implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    private final String id;

    public CorrelationId() {
        this.id = UUID.randomUUID().toString();
    }

    public CorrelationId(@NonNull String titulo) {
        this.id = titulo + "(" + UUID.randomUUID().toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorrelationId that = (CorrelationId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	public CorrelationId continueWith(String targetTopic) {
		return new CorrelationId(this.id + "-" + targetTopic);
		
	}
}
