package ed.ap.stage.Tijdsregistratie.aspect;

import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.ArrayList;
import java.util.Collection;

@ControllerAdvice
public class DtoMapperResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    private ModelMapper modelMapper;

    public DtoMapperResponseBodyAdvice(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && returnType.hasMethodAnnotation(Dto.class);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request,
                                           ServerHttpResponse response) {
        Dto ann = returnType.getMethodAnnotation(Dto.class);
        validate(ann);

        Class<?> dtoType = ann.value();
        Object value = bodyContainer.getValue();

        Object returnValue = getObjectConvertedToDto(dtoType, value);

        bodyContainer.setValue(returnValue);
    }

    private void validate(Dto ann) {
        Assert.state(ann != null, "No Dto annotation");
    }

    private Object getObjectConvertedToDto(Class<?> dtoType, Object value) {
        Object returnValue;

        if (value instanceof Page) {
            returnValue = ((Page<?>) value).map(it -> modelMapper.map(it, dtoType));
        } else if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            Collection<Object> objectCollection = new ArrayList<>();
            for (Object val : collection) {
                Object map = modelMapper.map(val, dtoType);
                objectCollection.add(map);
            }
            returnValue = objectCollection;
        } else {
            returnValue = modelMapper.map(value, dtoType);
        }

        return returnValue;
    }
}

