package net.github.rtc.app.utils.enums;

import junit.framework.Assert;
import net.github.rtc.app.model.entity.news.NewsStatus;
import net.github.rtc.app.model.entity.user.RoleType;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class EnumHelperTest {

    @Test
    public void testFindAll(){
        List res = Arrays.asList(NewsStatus.class.getEnumConstants());
        Assert.assertEquals(EnumHelper.findAll(NewsStatus.class), res);
    }

    @Test
    public void testGetNames() {
        List<String> res = new ArrayList<>();
        for (NewsStatus status : Arrays.asList(NewsStatus.class.getEnumConstants())) {
            res.add(status.name());
        }
        Assert.assertEquals(res , EnumHelper.getNames(NewsStatus.class));
    }

    @Test
    public void testGetValues() {
        List<String> res = new ArrayList<>();
        for (NewsStatus status : Arrays.asList(NewsStatus.class.getEnumConstants())) {
            res.add(status.toString());
        }
        Assert.assertEquals(res , EnumHelper.getValues(NewsStatus.class));
    }

    @Test
    public void testCreateMap() {
        Map<String, String> res = new HashMap<>();
        for (NewsStatus status : Arrays.asList(NewsStatus.class.getEnumConstants())) {
            res.put(status.name(), status.toString());
        }
        Assert.assertEquals(res , EnumHelper.createNameValueMap(NewsStatus.class));
    }

    @Test
    public void testContainsName() {
        assertTrue(EnumHelper.containsName(NewsStatus.class, "DRAFT"));
    }

    @Test
    public void testContainsValue() {
        assertTrue(EnumHelper.containsValue(NewsStatus.class, "DRAFT"));
    }

    @Test
    public void roleGetTypeByStringTest() {
        assertEquals(RoleType.ROLE_ADMIN, EnumHelper.getTypeByString(RoleType.class, "ROLE_ADMIN"));
    }
}
