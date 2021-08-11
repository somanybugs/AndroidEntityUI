package lhg.entityui.annotation;

import lhg.entityui.fieldview.FieldViewImpl;

import java.util.Map;

public interface FieldViewCreator<T> {

    FieldViewImpl create(T ann, Map<String, Object> props);

}
