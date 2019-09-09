package com.demoecommerce.domain.adapter;

import com.demoecommerce.domain.entity.CartProduct;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CartProductSerializer extends JsonSerializer<CartProduct> {
    @Override
    public void serialize(CartProduct cartProduct, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        if (cartProduct != null) gen.writeNumberField("id", cartProduct.getCartId());
        gen.writeEndObject();
    }
}
