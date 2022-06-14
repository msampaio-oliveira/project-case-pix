package br.com.project.pix.utils;

import br.com.project.pix.exception.CopyPropertyException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class NonNullBeanUtilsBeanTest {

    private static final Long ID1 = 1L;
    private static final Long ID2 = null;
    private static final String NAME1 = "nameA";
    private static final String NAME2 = "nameB";
    private static final String SURNAME1 = null;
    private static final String SURNAME2 = "surnameB";
    private static final Integer AGE1 = 30;
    private static final Integer AGE2 = null;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Spy
    private NonNullBeanUtilsBean utilsBeanMock;

    private A origA;
    private A destA;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        destA = new A(ID1, NAME1, SURNAME1, AGE1);
        origA = new A(ID2, NAME2, SURNAME2, AGE2);
    }

    @Test
    public void shouldCopyNonNullAttributes() throws Exception {
        utilsBeanMock.copyProperties(destA, origA);

        assertEquals("Attribute surname should be the same!", origA.getSurname(), destA.getSurname());
        assertEquals("Attribute name should be the same!", origA.getName(), destA.getName());
        assertNotEquals("Attribute age should not be the same!", origA.getAge(), destA.getAge());
        assertNotEquals("Attribute id should not be the same!", origA.getId(), destA.getId());
    }

    @Test
    public void shouldThrowCopyPropertyExceptionIfOneProblemOccursWhileCopyingBean() throws Exception {
        doThrow(new IllegalAccessException()).when(utilsBeanMock).callCopyOnSuperClass(any(), any(), any());
        expected.expect(CopyPropertyException.class);
        utilsBeanMock.copyProperties(destA, origA);
    }

    public static class A {
        private Long id;
        private String name;
        private String surname;
        private Integer age;

        public A(Long id, String name, String surname, Integer age) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.age = age;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
