/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pdfbox.util.operator;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.pdfviewer.PageDrawer;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
import org.apache.pdfbox.util.PDFOperator;

/**
 * 
 * @author <a href="mailto:andreas@lehmi.de">Andreas Lehmkühler</a>
 * @version $Revision: 1.0 $
 */
public class SetStrokingICCBasedColor extends OperatorProcessor 
{
    /**
     * scn Set color space for stroking operations.
     * @param operator The operator that is being executed.
     * @param arguments List
     * @throws IOException If an error occurs while processing the font.
     */
    public void process(PDFOperator operator, List arguments) throws IOException
    {
        PDColorState color = context.getGraphicsState().getStrokingColor();
        PDColorSpace cs = color.getColorSpace();
        int numberOfComponents = cs.getNumberOfComponents();
        float[] values = new float[numberOfComponents];
        for( int i=0; i<numberOfComponents; i++ )
        {
            values[i] = ((COSNumber)arguments.get( i )).floatValue();
        }
        color.setColorSpaceValue( values );
        
        if (context instanceof PageDrawer)
        {
            PageDrawer drawer = (PageDrawer)context;
            drawer.colorChanged(true);
        }
    }
}
