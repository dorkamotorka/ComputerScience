#!/usr/bin/env python

"""
Usage:
  pki status
  pki add <hostname>
  pki remove <hostname>
  pki distribute 
"""

import argparse

def status(argv):
    # TODO
    '''
    Print number of hostnames
    Print list of hostnames
    Print private key
    Print public key
    '''
    pass


def add(argv):
    # TODO
    '''
    Add hostname to file
    '''
    pass

def remove(argv):
    # TODO
    '''
    Remove hostname from file
    '''
    pass

def distribute(argv):
    # TODO
    '''
    Distribute public keys to hostnames
    Default ssh-password ubuntu
    '''
    pass

def main(argv=sys.argv[1:]):
    parser = argparse.ArgumentParser(usage=__doc__)
    parser.add_argument("command", help="Subcommand to run")

    parser.add_argument(
        "--version",
        action="version",
        version="%(prog)s {version}".format(version=__version__),
    )
    args = parser.parse_args(argv[:1])

    commands = {
        "status": status,
        "add": add,
        "remove": remove,
        "distribute": distribute,
    }

    if args.command in commands:
        commands[args.command](argv[1:])
