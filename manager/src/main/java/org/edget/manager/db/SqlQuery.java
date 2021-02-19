/*
* Copyright 2020 Huawei Technologies Co., Ltd.
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*     http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.edget.manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlQuery {

    public static void Insert (String query){
        String sql = query;
        System.out.println(sql);
        Connection c = null;
        Statement stmt = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://edget-db:5432/edgeT",
              "postgres", "123");
           c.setAutoCommit(false);
           stmt = c.createStatement();
           stmt.executeUpdate(sql);
           stmt.close();
           stmt = c.createStatement();
           c.commit();
           c.close();
        } catch ( org.postgresql.util.PSQLException e ) {
            System.out.println( e.getClass().getName()+": "+ e.getMessage());
        } catch ( Exception e ) {
           System.out.println( e.getClass().getName()+": "+ e.getMessage());
           System.exit(0);
        }
    }

    public static ResultSet Select (String query){
       String sql = query;
       System.out.println(sql);
       Connection c = null;
       Statement stmt = null;
       ResultSet Rs = null;

       try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://edget-db:5432/edgeT",
              "postgres", "123");
           c.setAutoCommit(false);
           System.out.println("Opened database successfully");
           stmt = c.createStatement();
           Rs = stmt.executeQuery(sql);
        } catch ( Exception e ) {
           System.out.println(" Inside sqlquery select ");
           System.err.println( e.getClass().getName()+": "+ e.getMessage() );
           System.exit(0);
        }
        return Rs;
    }
}
