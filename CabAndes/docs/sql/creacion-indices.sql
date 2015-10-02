// RF-12
CREATE INDEX INDEX_LC_COD_ADMINISTRADOR ON LOCAL(COD_ADMINISTRADOR);
CREATE INDEX INDEX_PD_COD_USUARIO ON PEDIDO(COD_USUARIO);
CREATE INDEX INDEX_PD_FECHA_CREACION ON PEDIDO(FECHA_CREACION);
CREATE INDEX INDEX_PD_FECHA_ENTREGA ON PEDIDO(FECHA_ENTREGA);

// RF-13
CREATE INDEX INDEX_PL_COD_LUGAR_ALMAC ON PRODUCTO_LUGAR_ALMAC(COD_LUGAR_ALMAC);
CREATE INDEX INDEX_PL_COD_PRESENTACION ON PRODUCTO_LUGAR_ALMAC(COD_PRESENTACION);
CREATE INDEX INDEX_LA_COD_TIPO_PRODUCTO ON LUGAR_ALMACENAMIENTO(COD_TIPO_PRODUCTO);

// RF-14, RF-15
CREATE INDEX INDEX_MB_COD_LUGAR_ORIGEN ON MOVIMIENTO(COD_LUGAR_ORIGEN);
CREATE INDEX INDEX_MB_COD_LUGAR_DESTINO ON MOVIMIENTO(COD_LUGAR_DESTINO);
CREATE INDEX INDEX_MB_FECHA_MOVIMIENTO ON MOVIMIENTO(FECHA_MOVIMIENTO);
CREATE INDEX INDEX_MB_COD_PRODUCTO ON MOVIMIENTO(COD_PRODUCTO);
CREATE INDEX INDEX_MB_COD_PRESENTACION ON MOVIMIENTO(COD_PRESENTACION);
CREATE INDEX INDEX_MB_CANTIDAD ON MOVIMIENTO(CANTIDAD);
