package com.sing.javassist;

import cn.hutool.core.util.StrUtil;
import javassist.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 * @author songbo
 * @since 2022-09-02
 */
public final class EntityHelperFactory {
    private EntityHelperFactory() {

    }

    /**
     * 获取实体助手
     *
     * @param entityClazz
     * @return
     */
    public static AbstractEntityHelper getEntityHelper(Class<?> entityClazz) {
        if (entityClazz == null) {
            return null;
        }
        ClassPool classPool = ClassPool.getDefault();
        classPool.appendSystemPath();

        // import java.sql.ResultSet;
        classPool.importPackage(ResultSet.class.getName());
        // import com.sing.javassist.UserEntity
        classPool.importPackage(entityClazz.getName());

        try {
            // 获取AbstractEntityHelper类
            CtClass ctClass = classPool.getCtClass(AbstractEntityHelper.class.getName());

            String helperClazzName = entityClazz.getName() + "_Helper";
            // 创建xxEntity_Helper extends AbstractEntityHelper
            CtClass ctClassHelper = classPool.makeClass(helperClazzName, ctClass);

            //创建默认构造器(public xEntity_Helper(){}
            CtConstructor ctConstructor = new CtConstructor(new CtClass[0], ctClassHelper);
            ctConstructor.setBody("{}");
            ctClassHelper.addConstructor(ctConstructor);

            // 创建方法
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" public Object create(java.sql.ResultSet resultSet) throws Exception {\n");
            stringBuilder.append(entityClazz.getName())
                    .append(" obj = new ").append(entityClazz.getName()).append("();\n");
            Field[] fields = entityClazz.getDeclaredFields();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }
                String columnName = column.name();
                stringBuilder.append("obj.set")
                        .append(StrUtil.upperFirst(field.getName()))
                        .append("(")
                        .append("(").append(field.getType().getName()).append(")")
                        .append("resultSet.getObject(\"")
                        .append(columnName)
                        .append("\"));\n");
            }
            stringBuilder.append("return obj;");
            stringBuilder.append("}");
            CtMethod ctMethod = CtNewMethod.make(stringBuilder.toString(), ctClassHelper);
            ctClassHelper.addMethod(ctMethod);

            // 生成代码到文件用于调试
            ctClassHelper.writeFile("G:/java-debug");

            Class<?> javaClazz = ctClassHelper.toClass();
            Object helperImpl = javaClazz.newInstance();
            return (AbstractEntityHelper) helperImpl;
        } catch (NotFoundException | CannotCompileException | IllegalAccessException | InstantiationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        EntityHelperFactory.getEntityHelper(UserEntity.class);
    }
}
