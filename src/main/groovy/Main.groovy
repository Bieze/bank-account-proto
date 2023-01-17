@Grapes([
        @Grab(group='org.xerial', module='sqlite-jdbc', version='3.40.0.0'),
        @Grab('info.picocli:picocli:4.7.0')

])
@GrabConfig(systemClassLoader=true)

import groovy.sql.Sql
import groovy.cli.picocli.CliBuilder

static void createTable() {
  def sql = Sql.newInstance('jdbc:sqlite:Library/Accounts/account.db', 'org.sqlite.JDBC')
  String sqlCreate = """
  CREATE TABLE IF NOT EXISTS accounts
  (
    owner                TEXT,
    accountId            INTEGER PRIMARY KEY AUTOINCREMENT,
    balance              BLOB
   );"""
  sql.execute(sqlCreate)
  sql.close()
}

static void main(String[] args) {
  createTable()
  def cli = new CliBuilder(name: "Bank account proto")
  cli.h(longOpt: "help", "Print this help message")
  cli.n(longOpt:"new", "Create new account", args: '1', argName: 'Owner name')

  def options = cli.parse(args)

  if (options.h) {
    cli.usage()

  }
  if(options.n) {
    def sql = Sql.newInstance('jdbc:sqlite:Library/Accounts/account.db', 'org.sqlite.JDBC')
    String sqlInsert = """
      INSERT INTO accounts (owner, balance) VALUES ("${options.n}", "0\$");
    """
    sql.execute(sqlInsert)
    sql.close()
  }
}