/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jdev.miniprofiler.examples.jooq;

import io.jdev.miniprofiler.examples.jooq.db.tables.pojos.Animal;
import org.jooq.DSLContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static io.jdev.miniprofiler.examples.jooq.db.tables.Animal.ANIMAL;

public class Handler extends HttpServlet {

    private DSLContext db;

    @Override
    public void init(ServletConfig config) throws ServletException {
        db = (DSLContext) config.getServletContext().getAttribute("db");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("animals", getAnimals());
        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }

    private List<Animal> getAnimals() {
        return db.select().from(ANIMAL).fetch().into(Animal.class);
    }
}
