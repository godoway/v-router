package me.gwsl.vrouter;

import me.gwsl.vrouter.annotation.ARouter;
import me.gwsl.vrouter.spi.BaseRouter;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@javax.annotation.processing.SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_8)
public class Processor extends AbstractProcessor {

    private static final String SERVICES_INF = "META-INF/services/" + BaseRouter.class.getName();
    private Set<Class<? extends Annotation>> supportedAnnotation = new HashSet<>();
    private Set<String> supported = new HashSet<>();

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();

        supportedAnnotation.add(ARouter.class);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotation.stream().map(Class::getName).collect(Collectors.toSet());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = annotations.stream().map(roundEnv::getElementsAnnotatedWith)
                .flatMap(Collection::stream)
                .filter(ele -> !ele.getKind().isInterface())
                .collect(Collectors.toSet());
        generateProviderInfo(elements);
        return true;
    }

    private void generateProviderInfo(Set<? extends Element> elements) {
        if (elements == null || elements.size() == 0) return;
        try (Writer w = filer.createResource(StandardLocation.CLASS_OUTPUT, "", SERVICES_INF).openWriter()) {
            for (Element ele : elements) {
                w.write(ele.toString());
                w.write("\n");
                messager.printMessage(Diagnostic.Kind.NOTE, "provider: " + ele.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
