package org.motechproject.mds.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.mds.dto.TypeDto;
import org.motechproject.mds.service.TypeService;
import org.motechproject.mds.web.SelectData;
import org.motechproject.mds.web.SelectResult;
import org.motechproject.mds.web.comparator.TypeDisplayNameComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testMdsContext.xml"})
public class AvailableControllerIT {

    @Resource(name = "mdsMessageSource")
    private MessageSource messageSource;

    @Autowired
    private TypeService typeService;

    @Autowired
    private AvailableController controller;

    @Test
    public void shouldReturnAllTypes() throws Exception {
        List<TypeDto> expected = typeService.getAllTypes();

        TypeDto longType = typeService.findType(Long.class);
        // The Long type is available for DDE exclusively, so it's not available
        // on the UI at all
        expected.remove(longType);

        Collections.sort(expected, new TypeDisplayNameComparator(messageSource));

        SelectData data = new SelectData("", 1, expected.size());
        SelectResult<TypeDto> result = controller.getTypes(data);

        assertEquals(expected, result.getResults());
    }

}