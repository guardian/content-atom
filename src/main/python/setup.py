import sys

from distutils.core import setup

version="[:VERSION:]"

setup(name='contentatom',
      version=version,
      url='https://theguardian.com',
      py_modules=[
		'contentatom.ttypes',
		'contentatom.constants',
		'contentatom.video.ttypes',
		'contentatom.video.constants'
	],
      )
