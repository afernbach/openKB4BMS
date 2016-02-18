/*
 *     openKB4BMS is an open source knowledge base (KB) acting as a
 *     building management application.
 *
 *     Copyright (C) 2016
 *     Institute of Computer Aided Automation, Automation Systems Group, TU Wien.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package at.ac.tuwien.auto.sewoa.obix;

import at.ac.tuwien.auto.sewoa.obix.data.ObixWatch;
import junit.framework.TestCase;

/**
 * Created by karinigor on 06.12.2015.
 */
public class ObixWatchParserTest extends TestCase {

    private String makeAnswer = "<obj href=\"/watch1\" is=\"obix:Watch\">\n"+
            "<op name=\"add\" href=\"/watch1/add\" in=\"obix:WatchIn\" out=\"obix:WatchOut\" />\n"+
            "<op name=\"remove\" href=\"/watch1/remove\" in=\"obix:WatchIn\" out=\"obix:Nil\" />\n"+
            "<reltime name=\"lease\" href=\"/watch1/lease\" val=\"PT60S\" writable=\"true\" />\n"+
            "<op name=\"pollChanges\" href=\"/watch1/pollChanges\" in=\"obix:WatchIn\" out=\"obix:WatchOut\" />\n"+
            "<op name=\"pollRefresh\" href=\"/watch1/pollRefresh\" in=\"obix:WatchIn\" out=\"obix:WatchOut\" />\n"+
            "<op name=\"delete\" href=\"/watch1/delete\" in=\"obix:Nil\" out=\"obix:Nil\" />\n"+
            " </obj>";

    ObixWatchParser tested = new ObixWatchParser();

    public void testParse() throws Exception {

        ObixWatch obixWatch = tested.parseWatch(makeAnswer);
        assertEquals("watch1", obixWatch.getName());
        assertEquals("/watch1", obixWatch.getHref());
        assertEquals(5, obixWatch.getOperations().size());

    }
}