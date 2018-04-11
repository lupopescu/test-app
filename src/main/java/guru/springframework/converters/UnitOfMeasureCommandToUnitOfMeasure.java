package guru.springframework.converters;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{
	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		if (source == null) {
            return null;
        }
		final UnitOfMeasure uom=new UnitOfMeasure();
		uom.setId(source.getId());
		uom.setDescription(source.getDescription());

		return uom;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		System.out.println("unimplementeg UnitOfMeasureCommandToUnitOfMeasure getInputType");
		return null;
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		System.out.println("unimplementeg UnitOfMeasureCommandToUnitOfMeasure getOutputType");
		return null;
	}

}
