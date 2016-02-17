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

import at.ac.tuwien.auto.sewoa.http.HttpRetriever;
import at.ac.tuwien.auto.sewoa.http.HttpRetrieverImpl;

/**
 * User: pelesic
 * Date: 01/07/15
 * Time: 20:48
 */
public class ObixRetriever {

    public static final String ENCODING = "UTF-8";
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    private String restURL;
    private HttpRetriever httpRetriever;

    public ObixRetriever(String restURL) {
        this.restURL = restURL;
        this.httpRetriever = new HttpRetrieverImpl();
    }

    public String retrieveObix() {
        byte[] bytes = httpRetriever.retrieveData(restURL);
        return XML_HEADER + new String(bytes);
    }
}
