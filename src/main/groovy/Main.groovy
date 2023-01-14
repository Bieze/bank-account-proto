@Grapes([
        @Grab(group='org.xerial', module='sqlite-jdbc', version='3.40.0.0'),
        @Grab('info.picocli:picocli:4.7.0')

])
@GrabConfig(systemClassLoader=true)

import groovy.sql.Sql
import groovy.cli.picocli.CliBuilder

static void main(String[] args) {
  def sql = Sql.newInstance('jdbc:sqlite:Library/Accounts/account.db', 'org.sqlite.JDBC')
  def cli = new CliBuilder(usage: 'Main.groovy [-a]')
  cli.with {
    c longOpt: 'create', args:1, argName: 'create', required: false, 'Create a new account'
  }

  def options = cli.parse(args)
  if (!options) {
    println "No arguments provided"
  } else if(options.c) {
    println sql.connection.toString()
    println options.c
  }
}