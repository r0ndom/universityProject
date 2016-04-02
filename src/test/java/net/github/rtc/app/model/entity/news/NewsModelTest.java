package net.github.rtc.app.model.entity.news;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(BlockJUnit4ClassRunner.class)
public class NewsModelTest {

    @Test
    public void testNewsConstructor() {
        try {
            new News();
            new News("X", "Y");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
