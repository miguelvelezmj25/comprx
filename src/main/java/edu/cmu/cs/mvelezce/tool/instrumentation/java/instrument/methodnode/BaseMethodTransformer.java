package edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.methodnode;

import edu.cmu.cs.mvelezce.tool.instrumentation.java.instrument.classnode.ClassTransformer;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.Set;

public abstract class BaseMethodTransformer implements MethodTransformer {

    private ClassTransformer classTransformer = null;

////    public BaseMethodTransformer() {
////
////    }
//
//    public BaseMethodTransformer(String path) throws InvocationTargetException, NoSuchMethodException, MalformedURLException, IllegalAccessException {
//        this.classTransformer = new DefaultBaseClassTransformer(path);
//    }

    public BaseMethodTransformer() {
    }

    public ClassTransformer getClassTransformer() {
        return classTransformer;
    }

    public void setClassTransformer(ClassTransformer classTransformer) {
        this.classTransformer = classTransformer;
    }

    public void transformMethods() throws IOException {
        Set<ClassNode> classNodes = this.classTransformer.readClasses();
        this.transformMethods(classNodes);
    }

    @Override
    public void transformMethods(Set<ClassNode> classNodes) throws IOException {
        for(ClassNode classNode : classNodes) {
            Set<MethodNode> methodsToInstrument = this.getMethodsToInstrument(classNode);

            if(methodsToInstrument.isEmpty()) {
                continue;
            }

            System.out.println("Transforming class " + classNode.name);

            for(MethodNode methodToInstrument : methodsToInstrument) {
                this.transformMethod(methodToInstrument);
            }

            this.classTransformer.writeClass(classNode, this.classTransformer.getPath() + "/" + classNode.name);
        }
    }
}