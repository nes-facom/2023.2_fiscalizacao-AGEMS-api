package fiscalizacao.dsbrs.agems.apis.dominio.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Cargo {
	COORDENADOR("Coordenador"),
	ANALISTA_DE_REGULACAO("Analista de Regulação"),
	ASSESSOR_TECNICO("Assessor Técnico"),
	ASSESSOR_JURIDICO("Assessor Jurídico");
	
	static {
		cargoMap = Arrays.stream(Cargo.values()).collect(Collectors.toMap(Cargo::getDescricao, Function.identity()));
	}
	
	private static Map<String, Cargo> cargoMap;
	private String descricao;

	private Cargo(String descricao) {
		this.descricao = descricao;
	}
	
	@JsonCreator
	public static Cargo getCargoByDescricao(String descricao) {
		return cargoMap.get(descricao);
	}

	@JsonValue
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
}
